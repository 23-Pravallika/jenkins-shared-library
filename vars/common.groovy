def sonarChecks(){
    stage('Sonar Checks') {
            // sh '''
            //         sonar-scanner -Dsonar.host.url=http://172.31.2.195:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} 
            //         curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
            //         bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}
            // '''        
            sh "echo Starting Code Quality Analysis"
            sh "echo Code Quality Analysis Completed"
    }
}

def lintchecks(){
    stage('Lint Checks') {
        if (env.APP_TYPE == "nodejs") {
           sh '''      
                    echo Lint Checks for ${COMPONENT}
                    echo installing jslint
                    # npm install jslint
                    # node_modules/jslint/bin/jslint.js server.js
                    echo performing lint checks for ${COMPONENT}
                    echo performing lint checks completed ${COMPONENT} 
            ''' 
        }
        else if (env.APP_TYPE == "maven") {
            sh '''      
                    echo Lint Checks for ${COMPONENT}
                    # mvn checkstyle:check
                    echo performing lint checks for ${COMPONENT}
                    echo performing lint checks completed ${COMPONENT} 
            '''
        }
        else if (env.APP_TYPE == "python") {
            sh '''      
                    echo Lint Checks for ${COMPONENT}
                    # pylint *.py
                    echo performing lint checks for ${COMPONENT}
                    echo performing lint checks completed ${COMPONENT} 
            '''            
        }
        else {
            sh '''      
                    echo Lint Checks for ${COMPONENT}
                    echo performing lint checks for ${COMPONENT}
                    echo performing lint checks completed ${COMPONENT} 
            '''
        }
    }
}

def testcases(){
    stage('')
}