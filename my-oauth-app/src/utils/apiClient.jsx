import axios from 'axios';

export const createSecureClient = (token, baseURL = 'http://localhost:9090') => {
  return axios.create({
    baseURL,
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
};