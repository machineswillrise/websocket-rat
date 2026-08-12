<!DOCTYPE html>
<html lang="en">
	<head>
		@template.common.head(title = "Set Credentials")
	</head>

	<body>
		<main class="container">
			<article class="card">
				<h4>Set Credentials</h4>
				<form action="/api/admin/set-creds" method="POST">
					<input type="text" placeholder="Username" name="username" required>
					<input type="password" placeholder="Password" name="password" required>
					<button type="submit">Done</button>
				</form>
			</article>
		</main>
	</body>
</html>
