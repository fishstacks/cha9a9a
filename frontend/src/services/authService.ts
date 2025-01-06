import axios from "axios";

const API_URL = "http://localhost:8082/api/auth";

export const register = async (username: string, email: string, password: string) => {
    const response = await axios.post(`${API_URL}/register`, { username, email, password });
    return response.data;
  };

export const login = async (email: string, password: string) => {
  const response = await axios.post(`${API_URL}/authenticate`, { email, password });
  return response.data};

