import jwtDecode from 'jwt-decode';
const token = authService.getAuthResult()?.access_token;
if (token) {
  const decoded = jwtDecode(token);
  useAuthStore.getState().setUser({
    username: decoded.sub,
    roles: decoded.roles || [],
    tenantId: decoded.tenant_id,
  });
}