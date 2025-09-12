import React, { useEffect, useRef } from 'react';
import { Navigate } from 'react-router-dom';
import { useAccessControl } from '../hooks/useAccessControl';
import { toast } from 'react-toastify';

const AccessGuard = ({ children, requiredRoles = [], fallback = null }) => {
  const { isAuthenticated, hasAccess, loading } = useAccessControl(requiredRoles);
  const hasShownToast = useRef(false);

  useEffect(() => {
    if (!loading && isAuthenticated && !hasAccess && !hasShownToast.current) {
      toast.error('🚫 Access Denied: You do not have permission to view this page.');
      hasShownToast.current = true;
    }
  }, [loading, isAuthenticated, hasAccess]);

  if (loading) return fallback || <div>🔐 Checking access...</div>;
  if (!isAuthenticated) return <Navigate to="/login" replace />;
  if (!hasAccess) return <Navigate to="/unauthorized" replace />;

  return children;
};

export default AccessGuard;