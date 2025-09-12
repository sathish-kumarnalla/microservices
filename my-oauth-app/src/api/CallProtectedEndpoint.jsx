import axios from 'axios';
import { useAuth } from '../auth/AuthProvider';

const callProtectedEndpoint = async () => {
  const { user } = useAuth();
  if (!user) return null;

  try {
    const response = await axios.get('http://localhost:9090/admin/users', {
      headers: {
        Authorization: `Bearer ${user.access_token}`,
      },
    });
    return response.data;
  } catch (error) {
    console.error('API call failed:', error);
    return null;
  }
};

export default callProtectedEndpoint;