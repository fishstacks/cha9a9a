import useFetchTransactions from "../hooks/useFetchTransactions";
import CreateTransaction from "./CreateTransaction";
import { ColumnDef } from "@tanstack/react-table";
import { useNavigate } from "react-router-dom";
import { TransactionDataTable } from "./TransactionDataTable";
import { useEffect } from "react";


const RecentTransactions = () => {
  const { addRealTimeTransaction, fetchTransactions, transactions } =
    useFetchTransactions(3);

  useEffect(() => {
    fetchTransactions(0);
  }, []); 

  const navigate = useNavigate();

  const dashboardTransactions = transactions.slice(0, 3);
  


  type transactionItem = {
    id: string
    amount: number
    type: string;
    date: string;
    description: string;
    categoryId: number;
    frequency: string | null;
  }

  const columns: ColumnDef<transactionItem>[] = [
    {
      accessorKey: "amount",
      header: "Amount",
    },
    {
      accessorKey: "type",
      header: "Type",
    },
    {
      accessorKey: "description",
      header: "Description",
    },
    {
      accessorKey: "categoryId",
      header: "Category",
    },{
      accessorKey: "frequency",
      header: "Frequency",
    },
  ]

  return (
    <div>
      <CreateTransaction onNewTransaction={addRealTimeTransaction} />

      <h2>Recently added Transactions</h2>

      {transactions.length === 0 ? (
        <p>No transactions available.</p>
      ) : (
        <div className="container mx-auto py-10">
          <TransactionDataTable columns={columns} data={dashboardTransactions} />
        </div>
      )}
      <button onClick={() => navigate("/history")}>Load More</button>
    </div>
  );
};

export default RecentTransactions;
