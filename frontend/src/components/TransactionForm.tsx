import { Input } from "./ui/input";
import { Checkbox } from "./ui/checkbox";
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger,} from "./ui/dialog";
import { CheckedState } from "@radix-ui/react-checkbox";
import { Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "./ui/select";
import { Button } from "./ui/button";
import { useState } from "react";
import { createTransaction } from "@/services/transactionService";

interface TransactionFormProps {
    onNewTransaction: (transaction: any) => void;
    token: string | null;
    categories: any[];
    iconMap: {
        [key: string]: React.ReactNode;
    }
    setCurrentForm: (form: "transactionForm" | "categoriesForm") => void;
  }

const TransactionForm: React.FC<TransactionFormProps> = ({
    onNewTransaction,
    token,
    categories, 
    iconMap,
    setCurrentForm
  }) => {

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement> | CheckedState) => {
        console.log("handleChange input:", e);
        if (typeof e === "boolean" || e === "indeterminate") {
            setForm((prev) => ({ ...prev, isFixed: e === "indeterminate" ? false : e }));
        } 
        else if ("target" in e) {
          const target = e.target;
          setForm((prev) => ({
            ...prev,
            [target.name]: target.value
          }));
        }
      };
    
      const handleSelectChange = (name: string, value: string) => {
        setForm((prev) => ({
          ...prev,
          [name]: value,
        }));
      };

    const [form, setForm] = useState({
        amount: "",
        type: "Expense",
        date: "",
        description: "",
        categoryId: 1,
        isFixed: false,
        frequency: "",
      });
    
      const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log("Form State before Submit:", form);
    
        try {
          const payload = {
            ...form,
            frequency: form.isFixed ? form.frequency : null,
          };
    
        console.log("Form State before Submit edited:", form);
        const newTransaction = await createTransaction(payload, token);
    
          alert("Transaction created!");
          onNewTransaction(newTransaction);
          setForm({
            amount: "",
            type: "Expense",
            date: "",
            description: "",
            categoryId: 1,
            isFixed: false,
            frequency: "",
          });
        } catch {
          alert("Error creating transaction");
        }
      };


    return (
        <>
        <DialogHeader>
          <DialogTitle>Create Transaction</DialogTitle>
          <DialogDescription>
            Create a new transaction here. Click save when you're done.
          </DialogDescription>
        </DialogHeader>
    <form onSubmit={handleSubmit}>
      <Input
        type="text"
        name="description"
        value={form.description}
        onChange={handleChange}
        placeholder="Description"
      />
      <Input
        type="number"
        name="amount"
        value={form.amount}
        onChange={handleChange}
        placeholder="Amount"
      />
      <Select name="type" value={form.type} onValueChange={(value) =>
      handleSelectChange("type", value)}>
        <SelectTrigger className="w-[180px]">
          <SelectValue placeholder="Select type" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem value="Expense">Expense</SelectItem>
            <SelectItem value="Income">Income</SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>
      <Input
        type="text"
        name="date"
        value={form.date}
        onChange={handleChange}
        placeholder="Date (YYYY-MM-DD)"
        pattern="\d{4}-\d{2}-\d{2}"
        title="Enter the date in YYYY-MM-DD format"
      />
      <Select name="categoryId" value={String(form.categoryId)} onValueChange={(value) =>
      handleSelectChange("categoryId", value)}>
        <SelectTrigger className="w-[180px]">
          <SelectValue placeholder="Select category" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
          {categories.map((category) => (
            <SelectItem key={category.categoryId} value={String(category.categoryId)}>
              {category.name} {iconMap[category.icon]} 
            </SelectItem>
          ))}
          </SelectGroup>
        </SelectContent>
      </Select>
      <Button onClick={() => setCurrentForm("categoriesForm")}>Edit categories</Button>
        <Checkbox
          id="isFixed"
          name="isFixed"
          checked={form.isFixed}
          onCheckedChange={(checked) => handleChange(checked)}
        />
        <label htmlFor="isFixed">Is Fixed</label>
      {form.isFixed &&(<Select name="frequency" value={form.frequency} onValueChange={(value) =>
      handleSelectChange("frequency", value) }>
        <SelectTrigger className="w-[180px]">
          <SelectValue placeholder="Select frequency" />
        </SelectTrigger>
        <SelectContent>
          <SelectGroup>
            <SelectItem value="Weekly">Weekly</SelectItem>
            <SelectItem value="Monthly">Monthly</SelectItem>
            <SelectItem value="Yearly">Yearly</SelectItem>
          </SelectGroup>
        </SelectContent>
      </Select>)}
      <Button type="submit">Create Transaction</Button>
    </form>
    </>
    )
}

export default TransactionForm;