# Distributed Tic Tac Toe game using Java RMI technology!

Build:
shell: cd distributed-systems-rmi
shell: mvn clean package

Run: (on example ip=127.0.0.1 and port=1098)

1. Server:
    shell: cd tictactoe-server
    shell: java -jar target/tic-tac-toe-server.jar 127.0.0.1 1098 /
            -Djava.security.policy=path/to/server/policy/file/server.policy

2. Clients: (remember to add info about server shared classes with -Djava.rmi.server.codebase)
    shell: cd tictactoe-client
    shell: java -jar target/tic-tac-toe-client.jar 127.0.0.1 1098 /
            -Djava.rmi.server.codebase=file:///home/put/your/path/here/distributed-systems-rmi/tictactoe-server/target/tic-tac-toe-server.jar /
            -Djava.security.policy=path/to/client/policy/file/client.policy

3. Run more clients...
