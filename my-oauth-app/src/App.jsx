import React, { useEffect } from 'react';
import { Routes, Route } from 'react-router-dom';
import Home from './pages/Home';
import Callback from './auth/Callback';
import Dashboard from './components/Dashboard';
import Layout from './components/Layout';
import ProtectedRoute from './auth/ProtectedRoute';
import AdminPage from './pages/AdminPage';
import Unauthorized from './pages/Unauthorized';

const App = () => {
  useEffect(() => {
    console.log('App component mounted');
  }, []);

  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        <Route
          index
          element={
            <>
              {console.log('Rendering Home route')}
              <Home />
            </>
          }
        />
        <Route
          path="callback"
          element={
            <>
              {console.log('Rendering Callback route')}
              <Callback />
            </>
          }
        />
        <Route
          path="dashboard"
          element={
            <>
              {console.log('Rendering Dashboard route')}
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            </>
          }
        />
        <Route
          path="admin"
          element={
            <>
              {console.log('Rendering AdminPage route')}
              <ProtectedRoute requiredRole="admin">
                <AdminPage />
              </ProtectedRoute>
            </>
          }
        />
        <Route
          path="unauthorized"
          element={
            <>
              {console.log('Rendering Unauthorized route')}
              <Unauthorized />
            </>
          }
        />
      </Route>
    </Routes>
  );
};

export default App;