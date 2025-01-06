
import SavingsCard from "@/components/SavingsCard";
import RecentTransactions from "../components/RecentTransactions";


const DashboardPage = () => {
  
  const username = "User"; 
  

  return (
    <div>
      <h1>Welcome, {username}</h1>
      <div style={{ display: "flex", gap: "20px" }}>
        <div>
          <SavingsCard />
          <RecentTransactions />
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;
