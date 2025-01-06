import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
  } from "@/components/ui/card"
import { AuthContext } from "@/context/AuthContext";
import { useContext, useEffect, useState } from "react";
import { getRealtimeSavings } from "../services/savingsService";


const SavingsCard = () => {
  const authContext = useContext(AuthContext);
  const token: string | null = authContext?.getToken() || null;

  const [savings, setSavings] = useState<string | null>(null);

  useEffect(() => {
    const fetchData = async () => {
        const result = await getRealtimeSavings(token);
        setSavings(result);
        console.log("Fetched Data:", savings);
    };

    fetchData();
}, []);


    
  

    return (
        <Card className="w-[380px]" >
          <CardHeader>
            <CardTitle>Savings</CardTitle>
            <CardDescription>At the moment</CardDescription>
          </CardHeader>
          <CardContent className="grid gap-4">
          {!savings ? (
            <div>No calculations available</div>
            ) : (
        <div className="flex items-center space-x-4 rounded-md border p-4">
            {savings}
        </div>
    )}
    

          </CardContent>
        </Card>
    );

};

export default SavingsCard;
