import { UserManager } from 'oidc-client-ts';

const oidcConfig = {
  authority: 'http://localhost:9090',
  client_id: 'react-client',
  redirect_uri: 'http://localhost:5173/callback',
  response_type: 'code',
  scope: 'openid profile email',
};

export const userManager = new UserManager(oidcConfig);