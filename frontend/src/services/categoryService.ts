import axios from "axios";

const API_URL = import.meta.env.API_URL;


export const fetchCategories = async (token: string | null) => {
  try {
    const headers = token ? { Authorization: `Bearer ${token}` } : {};
    const response = await axios.get(`${API_URL}/categories`, { headers });
    return response.data;
  } catch (error) {
    console.error("Error fetching categories", error);
    throw error;
  }
};

export const updateCategory = async (token: string | null, categoryId: string | number, updates: any) => {
    try {
      const headers = token ? { Authorization: `Bearer ${token}` } : {};
      const response = await axios.put(`${API_URL}/categories?categoryId=${categoryId}`, { categoryId, ...updates } , { headers });
      return response.data;
    } catch (error) {
      console.error("Error updating categories", error);
      throw error;
    }
}

export const deleteCategory = async (token: string | null, categoryId: string | number) => {
    try {
      const headers = token ? { Authorization: `Bearer ${token}` } : {};
      const response = await axios.delete(`${API_URL}/categories?categoryId=${categoryId}`, { headers });
      return response.data;
    } catch (error) {
      console.error("Error deleting categories", error);
      throw error;
    }
}

