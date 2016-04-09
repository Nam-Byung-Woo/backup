<?php
/**
* @file HTMLParser.php
*
* @class HTMLParser
*
* @bref ������Ʈ�� �ҽ��� �Ľ��Ͽ� ����� ������
* 
* @date 2014.01.23
*
* @author �ʱ�������(impactlife@naver.com)
*
* @section MODIFYINFO
*     - 2014.05.20 - ������ ������ ��� ���� ����� �����ϰ� �ߴµ� ���� �׳� �� �����Ѵ�
*
*/

class HTMLParser{
    
    private $url;
    private $refer;
    private $patterns;
    private $buffer;
    private $http_code;
    private $cookie_file;
    
    /**
    * @bref ������
    **/
    public function __construct(){
        $this->patterns = array();
        $this->buffer = '';
        $this->http_code = 0;
        $this->cookie_file = 'cookie.txt';
    }

    /**
    * @bref url ����
    * @param string URL
    **/    
    public function setUrl($url){
        $this->url = $url;
    }
    
    /**
    * @bref 2014.01.28 �߰� - REFERER ���� : refer������ ������ �ȳ����� ����Ʈ�� ����
    * @param string URL
    **/
    public function setRefer($refer){
        $this->refer = $refer;
    }

    /**
    * @bref ���ϰ� �Ľ̰���� row, col ����
    * @param string ����
    **/    
    public function addPattern($pattern){
        $this->patterns[] = $pattern;
    }
    
    /**
    * @bref ��Ű������ �����Ѵ�
    * @param string
    **/
    public function setCookieFile($filepath){
        $this->cookie_file = $filepath;
    }

    /**
    * @bref ������ url�� �������� �ҷ����δ�
    **/    
    private function loadContent(){
        
        //$agent = 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)'; 
        $agent = 'Mozilla/5.0 (Windows NT 6.1; rv:26.0) Gecko/20100101 Firefox/26.0';
        $curlsession = curl_init(); 
        curl_setopt ($curlsession, CURLOPT_URL,            $this->url); 
        curl_setopt ($curlsession, CURLOPT_HEADER,          1); 
        
        //http �����ڵ尡 302�϶� redirect_url �� ����
        curl_setopt ($curlsession, CURLOPT_FOLLOWLOCATION, TRUE);
        curl_setopt ($curlsession, CURLOPT_RETURNTRANSFER,  true); 
        
        curl_setopt ($curlsession, CURLOPT_POST,            0); 
        curl_setopt ($curlsession, CURLOPT_USERAGENT,      $agent); 
        curl_setopt ($curlsession, CURLOPT_REFERER,        $this->refer); 
        curl_setopt ($curlsession, CURLOPT_TIMEOUT,        3); 
        curl_setopt ($curlsession, CURLOPT_COOKIEJAR, $this->cookie_file);
        curl_setopt ($curlsession, CURLOPT_COOKIEFILE, $this->cookie_file);
        
        $this->buffer = curl_exec ($curlsession); 
        $cinfo = curl_getinfo($curlsession);
        
        $this->http_code = $cinfo['http_code'];
        curl_close($curlsession); 
        
        if ($this->http_code != 200) { 
            $this->buffer = '';
        }
    }
    
    /**
    * @bref ����� �����Ѵ�
    * @return array ��� ����� ��� �迭
    **/
    public function getResult(){
        
        $result = array();
        
        $this->loadContent();
        
        foreach($this->patterns as $item){
            $result[] = $this->getParseResult($item);
        }
        
        return $result;
    }
    
	public function getBuffer(){
        return $this->buffer;
    }

    /**
    * @bref �Ľ�
    * @param string ����
    * @return array �ϳ��� ���ԽĿ� ���� �Ľ� ����� ��� �迭
    **/
    private function getParseResult($pattern){
        $result = array();
        preg_match_all($pattern, $this->buffer, $matches);
        
        //ù��° ��Ҵ� ������
        if(count($matches) > 0)    array_splice($matches, 0, 1);
        
        return $matches;
    }
}