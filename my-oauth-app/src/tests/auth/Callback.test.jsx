import React from 'react';
import { render, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Callback from '../../auth/Callback';
import axios from 'axios';
import { useAuthStore } from '../../store/useAuthStore';

// Mock axios
jest.mock('axios');

// Mock useNavigate to prevent actual navigation
jest.mock('react-router-dom', () => ({
  ...jest.requireActual('react-router-dom'),
  useNavigate: () => jest.fn(),
}));

beforeEach(() => {
  useAuthStore.setState({ token: null }); // Reset Zustand before each test
});

test('stores token after callback', async () => {
  const mockToken = 'mocked-access-token';
  axios.post.mockResolvedValue({ data: { access_token: mockToken } });

  render(
    <MemoryRouter initialEntries={['/callback?code=test-code']}>
      <Callback />
    </MemoryRouter>
  );

  await waitFor(() => {
    expect(useAuthStore.getState().token).toBe(mockToken);
  });

  expect(axios.post).toHaveBeenCalledWith('/oauth/token', { code: 'test-code' });
});

test('handles missing code gracefully', async () => {
  const consoleErrorSpy = jest.spyOn(console, 'error').mockImplementation(() => {});

  render(
    <MemoryRouter initialEntries={['/callback']}>
      <Callback />
    </MemoryRouter>
  );

  await waitFor(() => {
    expect(consoleErrorSpy).toHaveBeenCalledWith('Authorization code not found');
    expect(useAuthStore.getState().token).toBe(null);
  });

  consoleErrorSpy.mockRestore();
});