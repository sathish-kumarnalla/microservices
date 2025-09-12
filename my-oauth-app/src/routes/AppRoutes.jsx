import { Routes, Route } from 'react-router-dom';
import Layout from '../components/Layout';
import Home from '../pages/Home';
import AdminPage from '../pages/AdminPage';
import Unauthorized from '../pages/Unauthorized';
import Callback from '../pages/Callback';
import Dashboard from '../pages/Dashboard';
import AccessGuard from '../auth/AccessGuard';

const protectedRoutes = [
  {
    path: 'dashboard',
    element: <Dashboard />,
    roles: ['USER', 'ADMIN'],
  },
  {
    path: 'admin',
    element: <AdminPage />,
    roles: ['ADMIN'],
  },
];

export default function AppRoutes() {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="callback" element={<Callback />} />
      <Route path="unauthorized" element={<Unauthorized />} />

      {/* Layout with nested routes */}
      <Route path="/" element={<Layout />}>
        <Route index element={<Home />} />

        {protectedRoutes.map(({ path, element, roles }) => (
          <Route
            key={path}
            path={path}
            element={<AccessGuard requiredRoles={roles}>{element}</AccessGuard>}
          />
        ))}
      </Route>
    </Routes>
  );
}