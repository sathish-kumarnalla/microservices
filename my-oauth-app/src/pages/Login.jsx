// src/pages/Login.jsx
import React from 'react';
import { useAuth } from 'react-oauth2-pkce';

const Login = () => {
  const { authService } = useAuth();

  const handleLogin = () => {
    authService.login();
  };

  return (
    <div style={styles.container}>
      <h2>Login to Your Account</h2>
      <button onClick={handleLogin} style={styles.button}>Login with OAuth2</button>
    </div>
  );
};

const styles = {
  container: {
    margin: '100px auto',
    width: '300px',
    padding: '20px',
    textAlign: 'center',
    background: '#fff',
    borderRadius: '8px',
    boxShadow: '0 0 10px rgba(0,0,0,0.1)',
  },
  button: {
    padding: '10px 20px',
    background: '#007bff',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    cursor: 'pointer',
  },
};

export default Login;