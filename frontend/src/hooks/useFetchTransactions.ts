import { useState, useContext } from "react";
import { getExpensesInfo, getRecentTransactions } from "../services/transactionService";
import { AuthContext } from "../context/AuthContext";

const useFetchTransactions = (pageSize = 10) => {
  const [transactions, setTransactions] = useState<any[]>([]);
  const [hasMore, setHasMore] = useState(true);
  const authContext = useContext(AuthContext);
  const token: string | null = authContext?.getToken() || null;

  const fetchTransactions = async (page: number) => {
    const data = await getRecentTransactions(page, pageSize, token);
    console.log("Fetched Data:", data);

    setTransactions((prev) => {
      const uniqueTransactions = [
        ...prev,
        ...data.content.filter(
          (newTx: { id: any }) => !prev.some((prevTx) => prevTx.id === newTx.id)
        ),
      ];
      return uniqueTransactions;
    });

    setHasMore(!data.last);
  };

   
    const fetchExpenses = async () => {
      const data = await getExpensesInfo(token);
      console.log("Fetched Data:", data);
    }  
  


  const addRealTimeTransaction = (newTransaction: any) => {
    setTransactions((prev) => {
      if (!prev.some((tx) => tx.id === newTransaction.id)) {
        return [newTransaction, ...prev];
      }
      return prev;
    });
  };



  return {
    fetchTransactions,
    addRealTimeTransaction,
    transactions,
    hasMore,
    fetchExpenses
  };
};

export default useFetchTransactions;
