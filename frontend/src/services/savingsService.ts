import axios from "axios";

const API_URL = import.meta.env.API_URL;


const getRealtimeSavings = async (token: string | null) => {
    try {
      const headers = token ? { Authorization: `Bearer ${token}` } : {};
      const response = await axios.get(`${API_URL}/savings/realtime`, { headers });
      console.log("API Response:", response.data);
      return response.data;
    } catch (error) {
      console.error("Error fetching transactions:", error);
      throw error;
    }
  };

  export { getRealtimeSavings };