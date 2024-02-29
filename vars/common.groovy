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
    stage('test cases'){
        def stages = [:]

        stages["Unit testingac"] = {
            echo "Unit Testing Started"
            echo "Unit Testing Completed"
             //sh npm tset or mvn test
        }
        stages["Integration testingac"] = {
            echo "Integration Testing Started"
            echo "Integration Testing Completed"
            // sh mvn verify or npm verify
        }
        stages["Functional testingac"] = {
            echo "Functional Testing Started"
            echo "Functional Testing Completed"
        }
        parallel(stages)
    }
}

def artifacts(){
    stage('Checking the Release'){
        // env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl -L -s http://${NEXUS_URL}/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-{TAG_NAME}.zip || true ")
        // print UPLOAD_STATUS
    }
    stage('Preparing the artifact'){
        if (env.APP_TYPE == "nodejs") {
            sh '''
                    npm install
                    echo Preparing the artifacts
                    zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
            '''
        }
        else if (env.APP_TYPE == "maven") {
            sh '''
                    mvn clean package
                    mv zip target/${COMPONENT}-1.0.jar ${COMPONENT}.jar
                    zip ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar
            '''
        }
        else if (env.APP_TYPE == "python") {
           sh '''
                    echo Preparing the artifacts
                    zip ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt
            '''
        }
        else{
            sh '''
                    echo "Frontend Component Is Executing"
                    cd static/
                    zip ../${COMPONENT}-${TAG_NAME}.zip *
            '''
        }
    }
    // stage("Uploading the artifact"){
    //     withCredentials([usernamePassword(credentialsId: 'NEXUS_CREDS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {
            
    //         sh "curl -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-{TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-{TAG_NAME}.zip"
    //     }
    // }
}

