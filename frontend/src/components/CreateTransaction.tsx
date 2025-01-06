import { useState, useContext } from "react";
import { AuthContext } from "../context/AuthContext";
import { Dialog, DialogContent, DialogTrigger} from "./ui/dialog";
import { Button } from "./ui/button";
import useFetchCategories from "@/hooks/useFetchCategories";
import { PartyPopper, BriefcaseBusiness, GraduationCap, Pizza, Fuel, Car } from "lucide-react";
import CategoriesForm from "./CategoriesForm.";
import TransactionForm from "./TransactionForm";

const CreateTransaction = ({ onNewTransaction }: { onNewTransaction: (transaction: any) => void }) => {
  const [currentForm, setCurrentForm] = useState<"transactionForm" | "categoriesForm">("transactionForm");

  

  const iconMap: { [key: string]: React.ReactNode } = {
    "Party-popper" : <PartyPopper />,
    "Briefcase-business" : <BriefcaseBusiness />,
    "Graduation-cap": <GraduationCap />,
    "Pizza": <Pizza />,
    "Fuel": <Fuel />,
    "Car": <Car />
  };

  const authContext = useContext(AuthContext);
  const token = authContext?.getToken() || null;

  const { data: categories, loading, error } = useFetchCategories(token);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>; 
  
  

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button variant="outline">Create new transaction</Button>
      </DialogTrigger>
      
      <DialogContent className="sm:max-w-[425px]">
      {currentForm === "transactionForm" && 
      ( <TransactionForm onNewTransaction={onNewTransaction} token={token} 
        categories={categories} setCurrentForm={setCurrentForm} iconMap={iconMap}/>
      )}
      {currentForm === "categoriesForm" && ( <CategoriesForm setCurrentForm={setCurrentForm} token={token} initialCategories={categories} iconMap={iconMap}/>
      )}
    </DialogContent>
  
    </Dialog>
  );
};

export default CreateTransaction;
