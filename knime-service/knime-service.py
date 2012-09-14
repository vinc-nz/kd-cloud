from BaseHTTPServer import BaseHTTPRequestHandler, HTTPServer
import cgi

class KnimeHandler(BaseHTTPRequestHandler):

	def do_POST(self):
		try:
			ctype, pdict = cgi.parse_header(self.headers.getheader('content-type'))
			if ctype == 'text/plain':
				cgi.

