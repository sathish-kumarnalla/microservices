import React, { useEffect } from 'react';
import { userManager } from '../auth/oidc-config';
import { useNavigate } from 'react-router-dom';

const Callback = () => {
  const navigate = useNavigate();

  useEffect(() => {
  userManager.signinRedirectCallback()
    .then(user => {
      console.log('User signed in:', user);
      navigate('/dashboard');
    })
    .catch(err => {
      console.error('Callback error:', err);
      navigate('/unauthorized');
    });
  }, [navigate]);

  return <div>🔄 Processing login...</div>;
};

export default Callback;