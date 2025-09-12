import React, { createContext, useContext, useEffect, useState } from 'react';
import { userManager } from './oidc-config';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    // Load user from storage on mount
    userManager.getUser().then(storedUser => {
      if (storedUser && !storedUser.expired) {
        setUser(storedUser);
      }
    });

    // Listen for login events
    userManager.events.addUserLoaded(loggedInUser => {
      console.log('User loaded via event:', loggedInUser);
      setUser(loggedInUser);
    });

    // Optional: handle logout
    userManager.events.addUserUnloaded(() => {
      console.log('User unloaded');
      setUser(null);
    });

    return () => {
      userManager.events.removeUserLoaded();
      userManager.events.removeUserUnloaded();
    };
  }, []);

  const login = () => userManager.signinRedirect();
  const logout = () => userManager.signoutRedirect();

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);