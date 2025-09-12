const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:9090';

export const API_ENDPOINTS = {
  TOKEN: `${BASE_URL}/oauth2/token`,
  AUTHORIZE: `${BASE_URL}/oauth2/authorize`,
  USER_INFO: `${BASE_URL}/api/user`,
  LOGOUT: `${BASE_URL}/logout`,
};