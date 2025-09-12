import React from 'react';
import { useAuth } from '../auth/AuthProvider';
import jwtDecode from 'jwt-decode';

const Header = () => {
  const { user, logout } = useAuth();

  if (!user) return null;

  const decoded = jwtDecode(user.access_token);
  const username = decoded.sub;
  const roles = decoded.roles.join(', ');

  return (
    <header>
      <p>👤 Logged in as: <strong>{username}</strong></p>
      <p>🔐 Roles: {roles}</p>
      <button onClick={logout}>Logout</button>
    </header>
  );
};

export default Header;