package edu.asu.mcmathe1.bscs.movielibraryserver;

import java.net.*;
import java.io.*;

/**
 * Copyright 2016 Michael Mathews
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Michael Mathews    mailto:Michael.C.Mathews@asu.edu
 * @version 2/26/2016
 */
public class MovieLibraryServer extends Thread {

	private Socket conn;
	private int id;
	private MovieLibrary library;
	private JsonRpcHandler jsonRpcHandler;

	public MovieLibraryServer(Socket sock, int id, MovieLibrary library) {
		this.conn = sock;
		this.id = id;
		this.library = library;
		this.jsonRpcHandler = new JsonRpcHandler(library);
	}

	public void run() {
		try {
			OutputStream outSock = conn.getOutputStream();
			InputStream inSock = conn.getInputStream();
			byte clientInput[] = new byte[4096]; // up to 1024 bytes in a message.
			int bytesRead = inSock.read(clientInput, 0, 4096);
			if (bytesRead != -1) {
				Thread.sleep(200);
				int length = bytesRead;
				while (inSock.available() > 0) {
					bytesRead = inSock.read(clientInput, length, 4096 - length);
					length = bytesRead + length;
					Thread.sleep(200);
				}
				String clientString = new String(clientInput, 0, length);
				if (clientString.indexOf("{") >= 0) {
					String request = clientString.substring(clientString.indexOf("{"));
					String response = jsonRpcHandler.callMethod(request);
					byte clientOut[] = response.getBytes();
					outSock.write(clientOut, 0, clientOut.length);
				} else {
					System.out.println("No json object in clientString: " + clientString);
				}
			}
			inSock.close();
			outSock.close();
			conn.close();
		} catch (Throwable t) {
			System.out.println("Can't get I/O for the connection.");
		}
	}

	public static void main(String args[]) {
		try {
			if (args.length < 1) {
				System.out.println("Usage: java -jar lib/server.jar portNum");
				System.exit(0);
			}

			int port = Integer.parseInt(args[0]);
			if (port <= 1024) {
				port = 8080;
			}

			MovieLibrary movieLibrary = new JsonFileMovieLibraryImpl();
			ServerSocket server = new ServerSocket(port);
			int id = 0;
			while (true) {
				System.out.println("Movie server waiting for connects on port " + port);
				Socket socket = server.accept();
				System.out.println("Movie server connected to client: " + id);
				MovieLibraryServer serverThread = new MovieLibraryServer(socket, id++, movieLibrary);
				serverThread.start();
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
