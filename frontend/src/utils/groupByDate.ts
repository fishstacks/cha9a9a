import { format, isToday, isYesterday } from "date-fns";

export const groupTransactionsByDate = (transactions: any[]) => {
  return transactions.reduce((groups: Record<string, any[]>, transaction) => {
    const date = format(new Date(transaction.date), "yyyy-MM-dd");
    if (!groups[date]) groups[date] = [];
    groups[date].push(transaction);
    return groups;
  }, {});
};

export const formatDateLabel = (date: string) => {
  const parsedDate = new Date(date);
  if (isToday(parsedDate)) return "Today";
  if (isYesterday(parsedDate)) return "Yesterday";
  return format(parsedDate, "MMMM dd, yyyy");
};
