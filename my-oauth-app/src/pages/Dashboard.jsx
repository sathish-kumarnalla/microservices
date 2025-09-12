// src/pages/Dashboard.jsx
import React from 'react';
import { useAccessControl } from '../hooks/useAccessControl';
import { useDashboardData } from '../hooks/useDashboardData';

const Dashboard = () => {
  const { isAuthenticated, hasAccess, loading: authLoading, decoded } = useAccessControl(['USER', 'ADMIN']);
  const { data: secureData, error, loading: dataLoading } = useDashboardData();

  if (authLoading) return <div>🔐 Checking access...</div>;
  if (!isAuthenticated) return <div>🔒 Please log in to view the dashboard.</div>;
  if (!hasAccess) return <div>🚫 Access Denied: You do not have permission to view this page.</div>;

  const { sub: username, roles = [], tenant_id: tenantId } = decoded;
  const isAdmin = roles.includes('ADMIN');

  return (
    <main style={{ padding: '2rem' }}>
      <h2>📊 Dashboard</h2>

      <section style={{ marginBottom: '1rem' }}>
        <p><strong>User:</strong> {username}</p>
        <p><strong>Tenant:</strong> {tenantId}</p>
        <p><strong>Roles:</strong> {roles.join(', ')}</p>
      </section>

      {isAdmin && (
        <section style={{ border: '1px solid #ccc', padding: '1rem', marginBottom: '1rem' }}>
          <h3>🛠 Admin Tools</h3>
          <p>Welcome, admin! You have elevated access.</p>
        </section>
      )}

      <section>
        <h3>🔐 Secure Data</h3>
        {dataLoading && <p>Loading secure data...</p>}
        {error && <p style={{ color: 'red' }}>{error}</p>}
        {secureData && (
          <pre style={{ background: '#f9f9f9', padding: '1rem', borderRadius: '4px' }}>
            {JSON.stringify(secureData, null, 2)}
          </pre>
        )}
      </section>
    </main>
  );
};

export default Dashboard;