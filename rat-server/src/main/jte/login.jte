<!DOCTYPE html>
<html lang="en">
	<head>
		@template.common.head(title = "Login")
	</head>

	<body>
		<main class="container">
			<article class="card">
				<h3>Login</h3>
				<form action="/api/admin/login" method="POST">
					<input type="text" placeholder="Username" name="username" required>
					<input type="password" placeholder="Password" name="password" required>
					<button type="submit">Login</button>
				</form>
			</article>
		</main>
	</body>
</html>
