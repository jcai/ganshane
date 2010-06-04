#	Copyright 2008 The Ganshane Network Service Team.
#	site:http://ganshane.net
#	file : $Id$
#	created at:2008-05-21


$KCODE = 'u'
require 'find'
require 'svn/client.rb'


dir = File.dirname(__FILE__)+'/../../'
Find.find(dir)  do |file|
  if file=~/\.java$/
    puts file
    
    tmp_file_name=file+'.tmp'
      tmpFile=File.open(tmp_file_name,'w')
      begin_read=true
      has_write_head=false
      File.open(file).each{|line|
        next if(begin_read&&!(line=~/package/))
        begin_read=false
        if(!has_write_head)
         currentYear = Time.now.year
        createdYear = 2010
        
        copyYears=createdYear.to_s+"-"+currentYear.to_s
        copyYears=currentYear.to_s if currentYear == createdYear
        
        puts copyYears
        
         tmpFile<<"/* 
 * Copyright #{copyYears} The Ganshane Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the \"License\");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */\n"
         has_write_head=true
         end
         tmpFile<<line
         }
      tmpFile.close
      File.rename(tmp_file_name,file)
  end
end
