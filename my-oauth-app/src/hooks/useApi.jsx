import { useAuth } from '../auth/AuthProvider';
import { useState } from 'react';
import { getUserProfile, getDashboardData } from '../services/apiService';

export const useApi = () => {
  const { user } = useAuth();
  const [loading, setLoading] = useState(false);

  const wrapRequest = async (fn) => {
    if (!user) throw new Error('User not authenticated');
    setLoading(true);
    try {
      return await fn(user.access_token);
    } catch (error) {
      console.error('API error:', error);
      throw error;
    } finally {
      setLoading(false);
    }
  };

  return {
    getUserProfile: () => wrapRequest(getUserProfile),
    getDashboardData: () => wrapRequest(getDashboardData),
    loading,
  };
};