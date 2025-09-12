import React from 'react';
import { useAuth } from '../auth/AuthProvider';
import { jwtDecode } from 'jwt-decode';

const Home = () => {
  const { user, login, logout } = useAuth();

  // Only decode if user exists
  const decoded = user?.access_token ? jwtDecode(user.access_token) : null;

  return (
    <div>
      <h2>Welcome</h2>
      {!user ? (
        <button onClick={login}>Login</button>
      ) : (
        <>
          <button onClick={logout}>Logout</button>
          <p><strong>Access Token:</strong></p>
          <code>{user.access_token}</code>

          {decoded && (
            <div style={{ marginTop: '1rem' }}>
              <p><strong>User ID:</strong> {decoded.sub}</p>
              <p><strong>Roles:</strong> {decoded.roles?.join(', ')}</p>
              <p><strong>Tenant:</strong> {decoded.tenant_id}</p>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Home;