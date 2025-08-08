import { useAuth } from "../context/AuthContext";

function DemoLoginPage() {
  const { login} = useAuth();

  const handleDemoLogin = async () => {
    try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ username: "demoUser", password: "demoPassword" }),
                credentials: "include",
                
            });

            if (!response.ok) {
                throw new Error("Login failed. Please check your credentials.");
            }

            const result = await response.json();
            login({ roles: result.roleNames, username: result.username }); // Store user info in context
            alert("Login successful!");
            window.location.href = "/"; // Redirect to home page after successful login
        } catch (error) {
            console.error("Error during login:", error);
            alert("Failed to log in. Please try again.");
        }
    };

  return(
    handleDemoLogin(),
    <div className="demo-login">
      <h1>Logging in as Demo user</h1>
    </div>
  );
}

export default DemoLoginPage;