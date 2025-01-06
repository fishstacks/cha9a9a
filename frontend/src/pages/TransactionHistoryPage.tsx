import { TransactionDataTable } from "@/components/TransactionDataTable";
import useFetchTransactions from "@/hooks/useFetchTransactions";
import { ColumnDef } from "@tanstack/react-table";
import { useEffect } from "react";
import { useSearchParams } from "react-router-dom";

const TransactionHistoryPage = () => {
  const [searchParams] = useSearchParams();
  const page = searchParams.get("page") || 0;
  const { fetchTransactions, transactions } = useFetchTransactions(4); 
  
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

  useEffect(() => {
    fetchTransactions(Number(page));
  }, [page]);

  return (
    <div>
      <h2>Transaction History</h2>
      <div className="container mx-auto py-10">
      <TransactionDataTable columns={columns} data={transactions} />
    </div>
    </div>
  );
};

export default TransactionHistoryPage;
