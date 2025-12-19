#include <fcntl.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>


int lireligne(int fd, char *buffer, int size) {
	ssize_t nbread = read(fd, buffer, size);
	if (nbread == -1) {
		return -1;
	}

	int i;
	for (i = 0; i < nbread; i++) {
		if (buffer[i] == '\n') {
			i++;
			break;
		}
	}
	lseek(fd, i - nbread, SEEK_CUR);
	return i;
}


int main(int argc, char **argv) {
    int fd_in = open(argv[1], O_RDONLY, 0664);  // descripteur de fichier du fichier ouvert en lecture
    int fd_out = open("imageNegative.pgm",  O_CREAT | O_WRONLY, 0664); // descripteur de fichier du fichier ouvert en écriture
    int nbread;
    unsigned char *buffer = malloc(4096 * sizeof(unsigned char));    // buffer de lecture
    for(int i =0 ; i<3 ; i++){
        nbread = lireligne(fd_in, buffer, 90);
        write(fd_out, buffer, *buffer);
    }
    nbread =1 ;
    while(nbread == 1){
        nbread = lireligne(fd_in, buffer, 1);
        buffer[0] = 255 - buffer[0];
        write(fd_out, buffer, 1);
    }

    close(fd_in);
    close(fd_out);
    return 0;
}