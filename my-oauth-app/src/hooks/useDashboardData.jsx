import { useState, useEffect } from 'react';
import { useAuth } from '../auth/AuthProvider';
import { getDashboardData } from '../services/apiService';

export const useDashboardData = () => {
  const { user } = useAuth();
  const [data, setData] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!user || !user.access_token) return;

    const fetchData = async () => {
      setLoading(true);
      try {
        const result = await getDashboardData(user.access_token);
        setData(result);
      } catch (err) {
        console.error('Dashboard fetch failed:', err);
        setError('Access denied or server error.');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [user]);

  return { data, error, loading };
};