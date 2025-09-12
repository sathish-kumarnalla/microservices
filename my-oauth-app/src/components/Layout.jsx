import React from 'react';
import { Outlet } from 'react-router-dom';

const Layout = () => {
  return (
    <div style={{ maxWidth: '1200px', margin: '0 auto', padding: '1rem' }}>
      <header>My App Header</header>
      <main>
        <Outlet />
      </main>
      <footer>© 2025 My App</footer>
    </div>
  );
};

export default Layout;