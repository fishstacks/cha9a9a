import { useContext } from "react";
import { Navigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

const ProtectedRoute = ({ children }: { children: JSX.Element }) => {
  const auth = useContext(AuthContext);

  if (auth?.loading) {
    return <div>Loading...</div>;
  }

  if (!auth?.token) {
    return <Navigate to="/login" />;
  }

  return children;
};

export default ProtectedRoute;
