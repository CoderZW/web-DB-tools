证书key及csr文件
sed -i "s/realtyprosfl.com/realtyprosfl.com/g" `grep realtyprosfl.com -rl /usr/leifeifei/ssl/realtyprosfl.com`
sed -i "s/realtyprosfl.com/realtyprosfl_com/g" `grep realtyprosfl.com -rl /usr/leifeifei/ssl/realtyprosfl.com`

openssl genrsa -out realtyprosfl.com.key 2048
openssl req -new -key realtyprosfl.com.key -out realtyprosfl.com.csr

cd /usr/leifeifei/ssl/realtyprosfl.com/
mv /home/leifeifei/下载/realtyprosfl.com.zip .
cp
cp f5592ebe0b4e01b8.crt realtyprosfl.com.crt
cat gd_bundle-g2-g1.crt >> realtyprosfl.com.crt


----------------nginx 修改
cd /home/web/nginx/conf/crts/
mkdir realtyprosfl.com
cd realtyprosfl.com
vi realtyprosfl.com.key
vi realtyprosfl.com.crt

cd /home/web/nginx/conf/server/ssl
vi realtyprosfl.com.ssl

ssl on;
ssl_certificate     crts/realtyprosfl.com/realtyprosfl.com.crt;
ssl_certificate_key   crts/realtyprosfl.com/realtyprosfl.com.key;

ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
ssl_prefer_server_ciphers  on;
ssl_ciphers 'ECDHE-RSA-AES128-GCM-SHA256:ECDHE-ECDSA-AES128-GCM-SHA256:ECDHE-RSA-AES256-GCM-SHA384:ECDHE-ECDSA-AES256-GCM-SHA384:DHE-RSA-AES128-GCM-SHA256:DHE-DSS-AES128-GCM-SHA256:kEDH+AESGCM:ECDHE-RSA-AES128-SHA256:ECDHE-ECDSA-AES128-SHA256:ECDHE-RSA-AES128-SHA:ECDHE-ECDSA-AES128-SHA:ECDHE-RSA-AES256-SHA384:ECDHE-ECDSA-AES256-SHA384:ECDHE-RSA-AES256-SHA:ECDHE-ECDSA-AES256-SHA:DHE-RSA-AES128-SHA256:DHE-RSA-AES128-SHA:DHE-DSS-AES128-SHA256:DHE-RSA-AES256-SHA256:DHE-DSS-AES256-SHA:DHE-RSA-AES256-SHA:ECDHE-RSA-DES-CBC3-SHA:ECDHE-ECDSA-DES-CBC3-SHA:AES128-GCM-SHA256:AES256-GCM-SHA384:AES128-SHA256:AES256-SHA256:AES128-SHA:AES256-SHA:AES:CAMELLIA:DES-CBC3-SHA:!aNULL:!eNULL:!EXPORT:!DES:!RC4:!MD5:!PSK:!aECDH:!EDH-DSS-DES-CBC3-SHA:!EDH-RSA-DES-CBC3-SHA:!KRB5-DES-CBC3-SHA';



realtyprosfl.com.ssl

cd /home/web/nginx/conf/server/
vi http_customize_site.server
www.realtyprosfl.com

vi https_customize_site.server

server {
	listen  443;
	server_name  realtyprosfl.com;

	include server/ssl/realtyprosfl.com.ssl;
	return 301 https://www.realtyprosfl.com$request_uri;
}
server {
	listen  443;
	server_name  www.realtyprosfl.com;

	include server/ssl/realtyprosfl.com.ssl;
	include server/locations/customize_site.location;
}

./../../sbin/nginx -t
./../../sbin/nginx -s reload