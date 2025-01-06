import { useState, useContext } from "react";
import { login, register } from "../services/authService";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { Input } from "@/components/ui/input";
import '../App.css';
import { Button } from "@/components/ui/button";

const AuthPage = () => {
  const navigate = useNavigate();
  
  const [isRegistering, setIsRegistering] = useState(false);
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [registerationStatus, setRegisterationStatus] = useState("");
  const authContext = useContext(AuthContext);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      if (isRegistering) {
      
        await register(username, email, password);
        setRegisterationStatus("Registration successful! Please log in.");
        setUsername("");
        setEmail("");
        setPassword("");
      } else {
        const { token } = await login(email, password);
        authContext?.login(token);
        navigate("/dashboard");
      }
    } catch (error) {
      console.error(isRegistering ? "Registration failed" : "Login failed", error);
      setRegisterationStatus("An error occurred. Please try again.");
    }
  };

  return (
    <div className="circle-toggle-wrapper">
      <div className="background-circle"></div>
      <div className={`foreground-circle ${isRegistering ? "register" : "login"}`}>
      <div className="content-wrapper">
        <form onSubmit={handleSubmit}>
          <h1>{isRegistering ? "Register" : "Login"}</h1>
          {isRegistering && (
            <Input type="text" placeholder="Username" required />
          )}
          <Input type="email" placeholder="Email" required />
          <Input type="password" placeholder="Password" required />
          <Button type="submit">{isRegistering ? "Register" : "Login"}</Button>
        </form>
        <Button onClick={() => setIsRegistering(!isRegistering)} className="circle-toggle-btn">
          {isRegistering ? "Switch to Login" : "Switch to Register"}
        </Button>
      </div>
      </div>
    </div>
  );
};

export default AuthPage;
