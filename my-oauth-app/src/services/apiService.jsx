import { createSecureClient } from '../utils/apiClient';

export const getUserProfile = async (token) => {
  const client = createSecureClient(token, 'http://localhost:9090');
  const response = await client.get('/userinfo');
  return response.data;
};

export const getDashboardData = async (token) => {
  const client = createSecureClient(token);
  const response = await client.get('/admin/dashboard');
  return response.data;
};