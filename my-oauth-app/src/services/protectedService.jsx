import { createSecureClient } from '../utils/apiClient';

export const fetchProtectedData = async (token) => {
  const client = createSecureClient(token);
  const response = await client.get('/admin/users');
  return response.data;
};