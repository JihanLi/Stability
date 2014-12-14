
br = [0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1];
sz = [1,2,3,4,5,6,7,8,9,10];
en = [0,0.1,0.2,0.3,0.4,0.5];
result = zeros(size(br,2),size(sz,2));

for i = 1:size(br,2)
    for j = 1:size(sz,2)
        filename = ['.\result\diff_BR' num2str(br(i)) '_EN0.2_SZ' num2str(sz(j)) '.txt'];
        diff=load(filename);
        result(i,j) = diff(end);
    end
end

mean_result = mean(result,2);

figure;
plot(br,mean_result);
xlabel('Balance Ratio');
ylabel('Difference of Convergence');

print(gcf,'-dpng','.\figures\balanceRatio_stability.png');


figure;
hold on;
for i = 1:size(result,1)
    r = rand();
    b = rand();
    g = rand();
    plot(sz,result(i,:),'Color',[r b g]);
end
xlabel('Clique Size');
ylabel('Difference of Convergence');
hold off;

print(gcf,'-dpng','.\figures\cliqueSize_stability.png');



result = zeros(1,size(en,2));
for i = 1:size(en,2)
    filename = ['.\result\diff_EN' num2str(en(i)) '_BR0.7_SZ10.txt'];
    diff=load(filename);
    result(1,i) = diff(end);
end

figure;
plot(en,result);
xlabel('New Edge Number');
ylabel('Difference of Convergence');

print(gcf,'-dpng','.\figures\newEdgeNumber_stability.png');
