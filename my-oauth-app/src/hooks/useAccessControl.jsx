// src/hooks/useAccessControl.js
import { useAuth } from '../auth/AuthProvider';
import { jwtDecode } from 'jwt-decode';
import { useMemo } from 'react';

export const useAccessControl = (requiredRoles = []) => {
  const { user, loading } = useAuth();

  const decoded = useMemo(() => {
    if (!user?.access_token) return null;
    try {
      return jwtDecode(user.access_token);
    } catch (err) {
      console.error('Token decoding failed:', err);
      return null;
    }
  }, [user]);

  const isAuthenticated = !!user && !!decoded;
  const userRoles = decoded?.roles || [];
  const hasAccess = requiredRoles.length === 0 || requiredRoles.every(role => userRoles.includes(role));

  return {
    isAuthenticated,
    hasAccess,
    loading,
    decoded,
    userRoles,
  };
};