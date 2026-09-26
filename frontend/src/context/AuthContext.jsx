import React, { createContext, useContext, useState, useEffect } from "react";
import { getCurrentUser, login as apiLogin, register as apiRegister, logout as apiLogout } from "../services/authService";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(getCurrentUser());
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    setUser(getCurrentUser());
    setLoading(false);
  }, []);

  const handleLogin = async (credentials) => {
    const data = await apiLogin(credentials);
    setUser(data);
    return data;
  };

  const handleRegister = async (userData) => {
    const data = await apiRegister(userData);
    setUser(data);
    return data;
  };

  const handleLogout = () => {
    apiLogout();
    setUser(null);
  };

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        isAdmin: user?.role === "ROLE_ADMIN",
        login: handleLogin,
        register: handleRegister,
        logout: handleLogout,
        loading,
      }}
    >
      {!loading && children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
