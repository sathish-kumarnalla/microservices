import { useAuthStore } from '../../store/useAuthStore';

describe('useAuthStore', () => {
  beforeEach(() => {
    useAuthStore.setState({ token: null, user: null });
  });

  test('initial state is null', () => {
    expect(useAuthStore.getState().token).toBe(null);
    expect(useAuthStore.getState().user).toBe(null);
  });

  test('setToken updates the state', () => {
    useAuthStore.getState().setToken('abc123');
    expect(useAuthStore.getState().token).toBe('abc123');
  });

  test('setUser updates the state', () => {
    const mockUser = { name: 'Alice', role: 'admin' };
    useAuthStore.getState().setUser(mockUser);
    expect(useAuthStore.getState().user).toEqual(mockUser);
  });

  test('logout clears token and user', () => {
    useAuthStore.getState().setToken('abc123');
    useAuthStore.getState().setUser({ name: 'Alice' });
    useAuthStore.getState().logout();

    expect(useAuthStore.getState().token).toBe(null);
    expect(useAuthStore.getState().user).toBe(null);
  });


  describe('useAuthStore with roles', () => {
    beforeEach(() => {
      useAuthStore.setState({ token: null, user: null });
    });

    test('hasRole returns true for matching role', () => {
      const mockUser = { name: 'Alice', roles: ['admin', 'editor'] };
      useAuthStore.getState().setUser(mockUser);

      expect(useAuthStore.getState().hasRole('admin')).toBe(true);
      expect(useAuthStore.getState().hasRole('editor')).toBe(true);
    });

    test('hasRole returns false for missing role', () => {
      const mockUser = { name: 'Bob', roles: ['viewer'] };
      useAuthStore.getState().setUser(mockUser);

      expect(useAuthStore.getState().hasRole('admin')).toBe(false);
    });

    test('hasRole returns false when user is null', () => {
      expect(useAuthStore.getState().hasRole('admin')).toBe(false);
    });
  });
});