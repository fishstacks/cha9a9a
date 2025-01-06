import axios from "axios";

const API_URL = import.meta.env.API_URL;

const getRecentTransactions = async (page: number, size: number, token: string | null) => {
  try {
    const headers = token ? { Authorization: `Bearer ${token}` } : {};
    const response = await axios.get(`${API_URL}/transactions/recent?page=${page}&size=${size}`, { headers });
    console.log(response.data);
    return response.data || { content: [], last: true };
  } catch (error) {
    console.error("Error fetching transactions:", error);
    throw error;
  }
};

export { getRecentTransactions };


export const createTransaction = async (
  transaction: {
    amount: string;
    type: string;
    date: string;
    description: string;
    categoryId: number;
    isFixed: boolean;
    frequency: string | null;
  },
  token: string | null
) => {
  try {
    const headers = token ? { Authorization: `Bearer ${token}` } : {};
    const response = await axios.post(`${API_URL}/transactions`, transaction, { headers });
    return response.data;
  } catch (error) {
    console.error("Error creating transaction:", error);
    throw error;
  }
};


export const getIncomesInfo = async (token: string | null) => {
  try {
    const headers = token ? { Authorization: `Bearer ${token}` } : {};
    const response = await axios.get(`${API_URL}/transactions/sum-of-incomes`, { headers });
    console.log(response.data);
    return response.data || { content: [], last: true };
  } catch (error) {
    console.error("Error fetching incomes:", error);
    throw error;
  }

}

export const getExpensesInfo = async (token: string | null) => {
  try {
    const headers = token ? { Authorization: `Bearer ${token}` } : {};
    const response = await axios.get(`${API_URL}/transactions/sum-of-expenses`, { headers });
    console.log(response.data);
    return response.data || { content: [], last: true };
  } catch (error) {
    console.error("Error fetching expenses:", error);
    throw error;
  }

}