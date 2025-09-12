import React, { useEffect } from 'react';
import { useAuth } from 'react-oauth2-pkce';

const LoginTest = () => {
  const { authService } = useAuth();

  useEffect(() => {
    console.log('LoginTest mounted');
    console.log('authService:', authService);
  }, [authService]);

  const handleLogin = () => {
    if (authService) {
      console.log('Calling login...');
      authService.login();
    } else {
      console.warn('authService is undefined');
    }
  };

  return (
    <div>
      <h2>Login Test</h2>
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default LoginTest;