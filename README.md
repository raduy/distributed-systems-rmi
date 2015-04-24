# Distributed Tic Tac Toe game using Java RMI technology!

Build:
shell: cd distributed-systems-rmi
shell: mvn clean package

Run: (on example ip=127.0.0.1 and port=1098)

1. Server:
    shell: cd tictactoe-server
    shell: java -jar target/tic-tac-toe-server.jar 127.0.0.1 1098

2. Clients:
    shell: cd tictactoe-client
    shell: java -jar target/tic-tac-toe-client.jar 127.0.0.1 1098

3. Run more clients...
