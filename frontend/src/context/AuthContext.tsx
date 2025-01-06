import { createContext, useState, useEffect, ReactNode } from "react";

interface AuthContextProps {
  token: string | null;
  login: (token: string) => void;
  logout: () => void;
  loading: boolean;
  getToken: () => string | null;
}

export const AuthContext = createContext<AuthContextProps | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [token, setToken] = useState<string | null>(null);
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    const storedToken = sessionStorage.getItem("authToken");
    if (storedToken) {
      setToken(storedToken);
    }
    setLoading(false);
  }, []);

  const login = (token: string) => {
    setToken(token);
    sessionStorage.setItem("authToken", token);
  };

  const logout = () => {
    setToken(null);
    sessionStorage.removeItem("authToken");
  };

  const getToken = () => token;

  return (
    <AuthContext.Provider value={{ token, login, logout, loading, getToken }}>
      {children}
    </AuthContext.Provider>
  );
};
