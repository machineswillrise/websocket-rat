@param String error = "An unknown error occurred."

<!DOCTYPE html>
<html lang="en">
	<head>
		@template.common.head(title = "Error")
	</head>

	<body>
		<main class="container">
			<article class="card">
				<h4>An error occurred:</h4>
				<p>${error}</p>
				<a href="/" role="button">Back To Home</a>
			</article>
		</main>
	</body>
</html>
