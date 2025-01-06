import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import AuthPage from "./pages/AuthPage";
import { AuthProvider } from "./context/AuthContext";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./components/ProtectectedRoute";
import { useEffect, useState } from "react";
import { Switch } from "./components/ui/switch";
import TransactionHistoryPage from "./pages/TransactionHistoryPage";

const App = () => {
  const [isDarkMode, setIsDarkMode] = useState(false);
  
  useEffect(() => {
    document.documentElement.classList.toggle("dark", isDarkMode);
  }, [isDarkMode]);
  
  return (
    <>
    <div className="app-header">
    <Switch onCheckedChange={() => setIsDarkMode(!isDarkMode)}>
        Switch to {isDarkMode ? "Light" : "Dark"} Mode
      </Switch>
    </div>
    <AuthProvider>   
      <Router>
        <Routes>
        <Route path="/auth" element={<AuthPage />} />
        <Route
          path="/dashboard"
          element={<ProtectedRoute><DashboardPage /></ProtectedRoute>}/>
        <Route path="/history" element={<ProtectedRoute><TransactionHistoryPage /></ProtectedRoute>} />
        <Route path="*" element={<Navigate to="/auth" />} />
        </Routes>
      </Router>
    </AuthProvider>
    </>
  );
};

export default App;
