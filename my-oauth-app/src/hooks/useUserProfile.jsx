import { useQuery } from '@tanstack/react-query';
import { useAuth } from '../auth/AuthProvider';
import { getUserProfile } from '../services/apiService';

export const useUserProfile = () => {
  const { user } = useAuth();

  return useQuery(['userProfile'], () => getUserProfile(user.access_token), {
    enabled: !!user,
    staleTime: 5 * 60 * 1000,
    cacheTime: 10 * 60 * 1000,
    refetchOnWindowFocus: false,
  });
};