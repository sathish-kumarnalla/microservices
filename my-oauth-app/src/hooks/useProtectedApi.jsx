import { useAuth } from '../auth/AuthProvider';
import { useState } from 'react';
import { fetchProtectedData } from '../services/protectedService';

export const useProtectedApi = () => {
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const callProtectedEndpoint = async () => {
    if (!user) {
      setError('User not authenticated');
      return null;
    }

    setLoading(true);
    try {
      const data = await fetchProtectedData(user.access_token);
      return data;
    } catch (err) {
      console.error('API call failed:', err);
      setError('Failed to fetch protected data');
      return null;
    } finally {
      setLoading(false);
    }
  };

  return { callProtectedEndpoint, loading, error };
};